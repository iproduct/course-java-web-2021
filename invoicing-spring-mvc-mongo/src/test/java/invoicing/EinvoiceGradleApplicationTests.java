package invoicing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import invoicing.dao.UserRepository;
import invoicing.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static invoicing.entity.Role.ADMIN;
import static invoicing.entity.Role.USER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = EinvoiceGradleApplication.class)
@AutoConfigureMockMvc
@Slf4j
class EinvoiceGradleApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void contextLoads() {
    }

    @Test
    void givenUsers_whenGetAllUsers_thenStatus200andJsonArray() throws Exception {
        given(userRepository.findAll()).willReturn(SAMPLE_USERS);

        MvcResult mvcResult = mockMvc.perform(get("/api/users")
                .with(user("admin").password("admin123").roles(ADMIN.toString()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> log.info(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$[0].username").value("admin"))
//                .andExpect(jsonPath("$[0].password").doesNotExist())
                .andExpect(jsonPath("$[1].username").value("user"))
//                .andExpect(jsonPath("$[1].password").doesNotExist())
                .andExpect(jsonPath("$[2].username").value("ivan"))
//                .andExpect(jsonPath("$[2].password").doesNotExist())
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        List<User> users = mapper.readValue(body, new TypeReference<List<User>>() {
        });
        List<User> usersWithourPasswords = SAMPLE_USERS.stream().map(u -> new User(u.getFirstName(), u.getLastName(), u.getUsername(), u.getPassword(), u.getRole(), u.isActive()))
                .collect(Collectors.toList());
        assertThat(users).hasSameElementsAs(usersWithourPasswords);

        then(userRepository).should(times(1)).findAll();
    }

    @Test
    void giveNewUser_whenUserPost_thenStatus201andLocationHeader() throws Exception {
        given(userRepository.insert(any(User.class))).willReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                    .with(user("admin").password("admin123").roles(ADMIN.toString()))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("location",
                        containsString("http://localhost/api/users/" + newUser.getId())))
                .andExpect(jsonPath("$.username").value(newUser.getUsername()))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }

    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "admin123", ADMIN, true),
            new User("Default", "User", "user", "user1234", USER, true),
            new User("Ivan", "Petrov", "ivan", "ivan1234")
    );

    private static final User newUser = new User("60d1930a576619366bf00b20","Hristo", "Dimitrov", "hristo", "hristo123", USER, true);


}
