package group.gamelife.security.demo.service;

import group.gamelife.security.demo.entity.Operator;
import group.gamelife.security.demo.entity.Role;
import group.gamelife.security.demo.repository.OperatorRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by xiongyizhou on 2019/8/15 10:57
 * E-mail: xiongyizhou@powerpms.com
 *
 * @author xiongyizhou
 */
@Service("userDetailService")
@Log
public class IUserDetailService implements UserDetailsService {

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Operator> result = operatorRepository.findOperatorByName(username);
        if(result.isPresent()){
            Operator account = result.get();
            Set<Role> roles = account.getRoles();

           // Collection<GrantedAuthority> authorities = new HashSet<>();

            Collection<GrantedAuthority> authorities = roles.stream().collect(HashSet::new,(temp,role)-> temp.add(new SimpleGrantedAuthority(role.getRoleName())),HashSet::addAll);
            log.info("username:"+account.getName());
            log.info("roles:"+authorities);
            return new User(account.getName(),account.getPassword(), authorities);
        }
        else {
            throw new UsernameNotFoundException("can not find this user / 查找用户失败");
        }
    }
}
