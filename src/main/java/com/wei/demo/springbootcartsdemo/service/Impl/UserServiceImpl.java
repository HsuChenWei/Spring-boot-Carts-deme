package com.wei.demo.springbootcartsdemo.service.Impl;

import com.wei.demo.springbootcartsdemo.entity.QUser;
import com.wei.demo.springbootcartsdemo.entity.Role;
import com.wei.demo.springbootcartsdemo.entity.User;
import com.wei.demo.springbootcartsdemo.entity.UserRole;
import com.wei.demo.springbootcartsdemo.model.Security.TokenPair;
import com.wei.demo.springbootcartsdemo.model.User.CreateUser;
import com.wei.demo.springbootcartsdemo.model.User.UserLogin;
import com.wei.demo.springbootcartsdemo.repository.RoleRepository;
import com.wei.demo.springbootcartsdemo.repository.UserRepository;
import com.wei.demo.springbootcartsdemo.repository.UserRoleRepository;
import com.wei.demo.springbootcartsdemo.service.Impl.querydsl.QuerydslRepository;
import com.wei.demo.springbootcartsdemo.service.UserService;
import com.wei.demo.springbootcartsdemo.service.utils.UtilService;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private QuerydslRepository querydsl;

    @Autowired
    private UtilService utilService;


    @Override
    public Option<User> createUser(CreateUser creation) {

        if (creation.getUserName() == null || creation.getUserName().trim().isEmpty()){
            throw new IllegalArgumentException("UserName can't be null or empty");
        }
        if (creation.getUserPwd() == null || creation.getUserPwd().trim().isEmpty()){
            throw new IllegalArgumentException("UserPwd can't be null or empty");
        }
        if (creation.getEmail() == null || creation.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("Email can't be null or empty");
        }
        if(userRepository.existsByUserName(creation.getUserName())){
            throw new RuntimeException("This UserName is existed.");
        }
        User user = new User();
        user.setUserName(creation.getUserName());
        String encryptPassword = passwordEncoder.encode(creation.getUserPwd());
        user.setUserPwd(encryptPassword);
        user.setEmail(creation.getEmail());
        user.setCreateAt(LocalTime.now());
        User newUser = userRepository.save(user);

        //同時設定UserRole為預設的0(一般會員)
        UserRole userRole = new UserRole();
        //userRole欄位1
        userRole.setUser(newUser);

        //從Role裡面找到0為User設定進UserRole的Table並儲存
        Role defaultRole = roleRepository.findById("0").orElseThrow(() -> new RuntimeException("Default role not found"));
        //userRole欄位2
        userRole.setRole(defaultRole);

        userRoleRepository.save(userRole);

        return Option.of(newUser);
    }

    @Override
    public Option<TokenPair> login(UserLogin login) {
        Option<User> userOption = getUserByUserName(login.getUserName());
        if (!userOption.isEmpty()){
            User user = userOption.get();
            if (checkPwd(login, user)){
                return utilService.generateTokenPair(user.getId());
            }else{
                throw new RuntimeException("User password is incorrect. Please,input again.");
            }
        }
        return Option.none();
    }

    public Option<User> getUserByUserName(String userName) {
        QUser user = QUser.user;
        return Option.of(querydsl.newQuery()
                .selectFrom(user)
                .where(user.userName.eq(userName))
                .fetchOne());
    }

    @Override
    public Option<User> getUserById(String id) {
        QUser user = QUser.user;
        return Option.of(querydsl.newQuery()
                .selectFrom(user)
                .where(user.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<String> getRoleIdsForUser(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()){
            throw new RuntimeException("User is not found!");
        }
        User user = userOptional.get();

        return user.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRole().getId())
                .collect(Collectors.toList());
    }


    private boolean checkPwd(UserLogin login, User dbUser){
        String inputPwd = login.getUserPwd();
        return passwordEncoder.matches(inputPwd, dbUser.getUserPwd());
    }
}
