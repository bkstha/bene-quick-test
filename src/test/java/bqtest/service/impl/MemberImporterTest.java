package bqtest.service.impl;

import bqtest.service.Member;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MemberImporterTest {

    @InjectMocks
    private MemberImporterImpl memberImporter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShouldReadFile() throws IOException {
//        File file = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("Members.txt")).getFile());
        File file = new File("Members.txt");
        List<Member> members = memberImporter.importMembers(file);
        Assert.assertFalse(members.isEmpty());
        Assert.assertEquals(108, members.size());
    }
}
