package za.co.yellowfire.sab.web;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;

@Named @RequestScoped @Slf4j
public class TestController implements Serializable {
    @Getter @Setter
    private Part uploadedFile;

    @Getter @Setter
    private String fileContent;

    public String onFileUpload() {
        log.info("ON UPLOAD");
        this.fileContent = "TEST";
        return null;
    }
}
