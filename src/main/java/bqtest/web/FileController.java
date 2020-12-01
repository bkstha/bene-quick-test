package bqtest.web;

import bqtest.service.Member;
import bqtest.service.MemberFileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private final MemberFileProcessor memberFileProcessor;

    public FileController(
            MemberFileProcessor memberFileProcessor
    ) {
        this.memberFileProcessor = memberFileProcessor;
    }

    // this api can be refactored by using cache implementation.
    // member path can be set to application startup
    @GetMapping(value = "/api/load-data")
    public Map<String, List<Member>> loadData(@Value("${config.file-to-load}") String fileToLoad) throws Exception {

        log.info("Got an request to load file {}", fileToLoad);

        File uploadedFile = new File(fileToLoad);

        Map<String, List<Member>> groupedMembers = processFile(uploadedFile);

        log.info("Successfully grouped to {} states ", groupedMembers.keySet().size());

        return groupedMembers;
    }

    private Map<String, List<Member>> processFile(File uploadedFile) throws Exception {
        /**
         * Use MemberFileProcessor to process the file
         */

        return this.memberFileProcessor.processFile(uploadedFile);
    }
}
