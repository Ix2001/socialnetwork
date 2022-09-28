package com.socialnetwork.org.user;

import com.socialnetwork.org.exceptions.PageException;
import com.socialnetwork.org.exceptions.UserAlreadyExistException;
import com.socialnetwork.org.exceptions.UserDoesNotExistException;
import com.socialnetwork.org.followers.Followers;
import com.socialnetwork.org.followers.FollowersRepository;
import com.socialnetwork.org.pictures.UserPictures;
import com.socialnetwork.org.pictures.UserPicturesRepository;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Log4j2
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserPicturesRepository userPicturesRepository;
    private final FollowersRepository followersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserPicturesRepository userPicturesRepository, FollowersRepository followersRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userPicturesRepository = userPicturesRepository;
        this.followersRepository = followersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserData userData) {
        if (userRepository.findByUsername(userData.getUsername()).isPresent()) {
            throw new UserAlreadyExistException(userData.getUsername());
        }
        else{
            userData.setPassword(passwordEncoder.encode(userData.getPassword()));
            userData.setRoles(Collections.singleton(Role.USER));
            userData.setIsActive(false);
            userData.setIsBanned(false);
            userData.setIsDeleted(false);
            userRepository.save(userData);
        }
    }


    public UserPublicDTO getPersonByUsername(String username) {
        log.info("Getting UserData by username: {}", username);
        UserData userData = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        UserPublicDTO UserPublicDTO = new UserPublicDTO();
        BeanUtils.copyProperties(userData, UserPublicDTO);
        UserPublicDTO.setFollowersCount(userData.getFollowers().size());
        UserPublicDTO.setFollowingCount(userData.getFollowing().size());
        return UserPublicDTO;
    }

    public List<UserBasicPublicDTO> getFollowers(String username, int page) {
        if (page - 1 < 0) {
            throw new PageException("Page number can't be less than 0");
        }
        PageRequest pageRequest = PageRequest.of(page, 10);
        UserData userData = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        List<UserBasicPublicDTO> followers = new ArrayList<>();
        List<Followers> followersList = followersRepository.findByTo(userData, pageRequest);
        for (Followers follower : followersList) {
            UserBasicPublicDTO UserBasicPublicDTO = new UserBasicPublicDTO();
            BeanUtils.copyProperties(follower.getFrom(), UserBasicPublicDTO);
            followers.add(UserBasicPublicDTO);
        }
        return followers;
    }

    public List<UserBasicPublicDTO> getFollowing(String name, int page) {
        if (page - 1 < 0) {
            throw new PageException("Page number can't be less than 0");
        }
        PageRequest pageRequest = PageRequest.of(page, 10);
        UserData UserData = userRepository.findByUsername(name).orElseThrow(() -> new UserDoesNotExistException(name));
        List<UserBasicPublicDTO> following = new ArrayList<>();
        List<Followers> followingList = followersRepository.findByFrom(UserData, pageRequest);
        for (Followers follower : followingList) {
            UserBasicPublicDTO UserBasicPublicDTO = new UserBasicPublicDTO();
            BeanUtils.copyProperties(follower.getTo(), UserBasicPublicDTO);
            following.add(UserBasicPublicDTO);
        }
        return following;
    }


    public void deletePerson(String username) {
        UserData UserData = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        UserData.setIsDeleted(true);
        userRepository.save(UserData);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData UserData = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (UserData == null) {
            throw new UserDoesNotExistException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        UserData.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
        return new User(UserData.getUsername(), UserData.getPassword(), authorities);
    }

    public void uploadProfilePicture(String username, UserBasicPublicDTO base64) {
        String base64String = base64.getProfilePicture();
        String[] base64Array = base64String.split("[:/;]");
        String type = base64Array[1];
        if (type.equals("image")) {
            String rootLocation = "src/main/resources/static/content/" + username + "/UserData/images/";
            loadContent(username, base64String, rootLocation);

        } else if (type.equals("video")) {
            String rootLocation = "src/main/resources/static/content/" + username + "/UserData/videos/";
            loadContent(username, base64String, rootLocation);
        } else {
            log.error("Wrong type of file");
            throw new RuntimeException("Wrong type of file");
        }
    }

    private void loadContent(String username, String base64, String rootLocation) {
        Path path = Paths.get(rootLocation);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String filename = rootLocation + username + "." + System.currentTimeMillis() + ".txt";
        UserData UserData = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        UserPictures personPhotos = new UserPictures();
        personPhotos.setUser(UserData);
        personPhotos.setPhotoPath(filename);
        personPhotos.setDateOfPhoto(LocalDateTime.now());
        userPicturesRepository.save(personPhotos);
        userRepository.save(UserData);
        try {
            PrintWriter writer = new PrintWriter(filename, UTF_8);
            writer.println(base64);
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public UserPublicDTO updatePerson(String name, UserPublicDTO UserPublicDTO) {
        UserData UserData = userRepository.findByUsername(name).orElseThrow(() -> new UserDoesNotExistException(name));
        BeanUtils.copyProperties(UserPublicDTO, UserData, getNullPropertyNames(UserPublicDTO));
        userRepository.save(UserData);
        return UserPublicDTO;
    }


    public void banPerson(String username) {
        UserData UserData = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        UserData.setIsBanned(true);
        userRepository.save(UserData);
    }

    public void unbanPerson(String username) {
        UserData UserData = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        UserData.setIsBanned(false);
        userRepository.save(UserData);
    }

    public void undeletePerson(String username) {
        UserData UserData = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        UserData.setIsDeleted(false);
        userRepository.save(UserData);
    }

    public void makeAdmin(String username) {
        UserData UserData = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        UserData.setRoles(Collections.singleton(Role.ADMIN));
        userRepository.save(UserData);
    }

    public void makeUser(String username) {
        UserData UserData = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        UserData.setRoles(Collections.singleton(Role.USER));
        userRepository.save(UserData);
    }

    public void getMostPopular() {
        ;
    }

    public void followPerson(String name, String username) {
        UserData UserData = userRepository.findByUsername(name).orElseThrow(() -> new UserDoesNotExistException(name));
        UserData personToFollow = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        if (UserData.getFollowing().stream().anyMatch(followers -> followers.getTo().equals(personToFollow))) {
            followersRepository.delete(followersRepository.findByFromAndTo(UserData, personToFollow));
        }
        else {
            Followers followers = new Followers();
            followers.setFrom(UserData);
            followers.setTo(personToFollow);
            followersRepository.save(followers);
        }
    }
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public List<UserBasicPublicDTO> searchPersonByUsernameContaining(String username, int page) throws PageException {
        if (page - 1 < 0) {
            throw new PageException("Page number cannot be less than 0");
        }
        else {
            PageRequest pageable = PageRequest.of(page - 1, 2);
            List<UserData> persons = userRepository.findByUsernameContaining(username, pageable);
            List<UserBasicPublicDTO> personBasicPublicDTOS = new ArrayList<>();
            for (UserData UserData : persons) {
                UserBasicPublicDTO UserBasicPublicDTO = new UserBasicPublicDTO();
                BeanUtils.copyProperties(UserData, UserBasicPublicDTO);
                personBasicPublicDTOS.add(UserBasicPublicDTO);
            }
            return personBasicPublicDTOS;
        }
    }

}
