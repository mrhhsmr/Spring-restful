package com.tty.spring.Springrestful.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.verify;


@RunWith(SpringRunner.class)
@WebMvcTest(value = UserResource.class)
public class UserResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDaoService userDaoService;

    private User mockUser = new User(5, "Christina");
    private List<User> list = new ArrayList<>();
    private String postTest = "{\"name\":\"Christina\"}";

    @Test
    public void getAllStatusCode() throws Exception {
        Mockito.when(userDaoService.getAllUsers()).thenReturn(list);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertTrue(result.getResponse().getStatus() == 200);
        Assert.assertTrue(result.getResponse().getContentAsString().equals("[]"));

    }

    @Test
    public void getAllBody() throws Exception {
//        Mockito.when(userDaoService.getAllUsers()).thenReturn(list);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println("hello  asdada " +  result.getResponse().getContentAsString());
        Assert.assertTrue(result.getResponse().getContentAsString().equals("[]"));

    }
    @Test
    public void getUser() throws Exception {
        Mockito.when(userDaoService.getUser(5)).thenReturn(mockUser);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/users/5").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"id\":5,\"name\":\"Christina\",\"_links\":{\"all-users\":{\"href\":\"http://localhost/users\"}}}";
        JSONAssert.assertEquals(result.getResponse().getContentAsString(), expected, true);
    }

    @Test
    public void deleteUser() throws Exception {
        Mockito.when(userDaoService.DeleteById(1)).thenReturn(mockUser);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/users/1").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void createUserStatusCode() throws Exception {
        Mockito.when(userDaoService.saveUser(Mockito.any(User.class))).thenReturn(mockUser);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/users").accept(MediaType.APPLICATION_JSON)
                        .content(postTest).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void createUser() throws Exception {
        Mockito.when(userDaoService.saveUser(Mockito.any(User.class))).thenReturn(mockUser);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/users").accept(MediaType.APPLICATION_JSON).
                        content(postTest).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals("http://localhost/users/5", response.getHeader(HttpHeaders.LOCATION));
    }

}