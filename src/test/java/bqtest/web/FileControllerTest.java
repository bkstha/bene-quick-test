package bqtest.web;

import bqtest.service.Member;
import bqtest.service.MemberFileProcessor;
import com.jayway.jsonpath.JsonPath;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(FileController.class)
public class FileControllerTest extends TestCase {

    private MockMvc mvc;

    @Mock
    private MemberFileProcessor memberFileProcessor;

    @InjectMocks
    private FileController fileController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    public Member getMockMember() {
        Member member = new Member();
        member.setId("123");
        member.setFirstName("bikash");
        member.setLastName("shrestha");
        member.setAddress("kalanki");
        member.setCity("kathmandu");
        member.setState("bagmati");
        member.setZip("11406");
        return member;
    }

    @Test
    public void testShouldLoadData() throws Exception {
        Map<String, List<Member>> memberMap = new HashMap<>();
        Member member = getMockMember();
        memberMap.put("bagmati", Collections.singletonList(member));//TODO add more data
        Mockito.when(memberFileProcessor.processFile(Mockito.any(File.class))).thenReturn(memberMap);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/load-data"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Assert.assertNotNull("Response should not be null", contentAsString);
        Assert.assertEquals("First name should be equal", "bikash", JsonPath.read(contentAsString, "$.bagmati[0].firstName")); //TODO add others aswell
    }

    @Test
    public void testShouldReturnEmptyData() throws Exception {
        Map<String, List<Member>> memberMap = new HashMap<>();
        Mockito.when(memberFileProcessor.processFile(Mockito.any(File.class))).thenReturn(memberMap);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/load-data"))
                .andExpect(status()
                        .isOk())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Assert.assertNotNull("Response should not be null", contentAsString);
        Assert.assertEquals("Should return empty", "{}", contentAsString);


    }
}
