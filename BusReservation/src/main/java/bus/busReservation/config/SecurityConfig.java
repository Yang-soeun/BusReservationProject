package bus.busReservation.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig{

    @Bean //빈으로 등록 : 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    public BCryptPasswordEncoder bCryptPasswordEncoder() { //패스워드 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchyImpl(){
        log.info("실행");
        RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
        roleHierarchyImpl.setHierarchy("ADMIN > MANAGER > USER");
        return roleHierarchyImpl;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/reservation/**").authenticated() //인증만 되면 들어갈 수 있는 주소!!
                .antMatchers("/bus").hasAnyAuthority("BUS")
                .antMatchers("/bus/joinForm").hasAnyAuthority("MANAGER")
                .anyRequest().permitAll() //위의 주소가 아니면 누구나 들어갈 수 있음

                .and()

                .formLogin()
                .loginPage("/loginForm") //미인증 사용자가 접근하면 이 페이지로 돌아옴
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
                .usernameParameter("id")
                .defaultSuccessUrl("/")//로그인 성공 후 이동 페이지.
//                .successHandler(new LoginSuccessHandler())
                .and()
                .logout().logoutUrl("/doLogout").logoutSuccessUrl("/");
        return http.build();
    }
}
