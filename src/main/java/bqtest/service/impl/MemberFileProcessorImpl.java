package bqtest.service.impl;

import bqtest.service.Member;
import bqtest.service.MemberFileProcessor;
import bqtest.service.MemberImporter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemberFileProcessorImpl extends MemberFileProcessor {

    /*
     * Implement methods here
     */
    MemberImporter memberImporter;

    public MemberFileProcessorImpl(MemberImporter memberImporter) {
        this.memberImporter = memberImporter;
    }

    @Override
    protected MemberImporter getMemberImporter() {
        return this.memberImporter;
    }

    @Override
    protected List<Member> getNonDuplicateMembers(List<Member> membersFromFile) {
        List<Member> memberList = new ArrayList<>();
        for (Member member : membersFromFile) {
            if (!memberList.contains(member)) {
                memberList.add(member);
            }
        }
        return memberList;
    }

    @Override
    protected Map<String, List<Member>> splitMembersByState(List<Member> validMembers) {
        return validMembers.stream().collect(Collectors.groupingBy(Member::getState));
    }

}
