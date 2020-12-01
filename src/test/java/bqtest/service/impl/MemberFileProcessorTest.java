package bqtest.service.impl;

import bqtest.service.Member;
import bqtest.service.MemberImporter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberFileProcessorTest {


    @Mock
    MemberImporterImpl memberImporter;

    @InjectMocks
    private MemberFileProcessorImpl memberFileProcessor = new MemberFileProcessorImpl(memberImporter);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private Member getMockMember() {
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
    public void testShouldProcessFile() throws Exception {
//        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("Members.txt")).getFile());
        File file = new File("Members.txt");
        Map<String, List<Member>> stringListMap = memberFileProcessor.processFile(file);
        System.out.println(stringListMap);
        Assert.assertNotNull(stringListMap);
    }

    @Test
    public void testShouldGetMemberImporter() throws Exception {
        MemberImporter memberImporter = memberFileProcessor.getMemberImporter();
        Assert.assertNotNull(memberImporter);
    }

    @Test
    public void testShouldGetNonDuplicateMembers() throws Exception {
        List<Member> memberList1 = new ArrayList<>();
        memberList1.add(getMockMember());
        memberList1.add(getMockMember());
        List<Member> memberList = memberFileProcessor.getNonDuplicateMembers(memberList1);
        Assert.assertNotNull(memberList);
        Assert.assertEquals(1, memberList.size());
    }

    @Test
    public void testShouldSplitMembersByState() throws Exception {
        List<Member> memberList2 = new ArrayList<>();
        memberList2.add(getMockMember());

        Map<String, List<Member>> memberList = memberFileProcessor.splitMembersByState(memberList2);
        Assert.assertNotNull(memberList);
        Assert.assertEquals(1, memberList.size());
        Assert.assertEquals(true, memberList.containsKey("bagmati"));
        Assert.assertEquals(1, memberList.get("bagmati").size());

    }


}
