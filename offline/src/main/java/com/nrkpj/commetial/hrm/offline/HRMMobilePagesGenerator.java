package com.nrkpj.commetial.hrm.offline;

import static com.nrkpj.commetial.hrm.utils.ServiceFactory.initializeSpringContext;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpException;

import com.nrkpj.commetial.hrm.core.dos.House;

public class HRMMobilePagesGenerator implements PageGenerator<House> {

    private final Collection<PageGenerator<House>> pageGenerators;

    public HRMMobilePagesGenerator(Collection<PageGenerator<House>> pageGenerators) {
        this.pageGenerators = pageGenerators;
    }

    public void generate(Collection<House> houses) throws IOException {
        File generatedFilesDirectory = new File("generated");
        FileUtils.deleteDirectory(generatedFilesDirectory);
        FileUtils.forceMkdir(generatedFilesDirectory);
        for (PageGenerator<House> pageGenerator : pageGenerators) {
            pageGenerator.generate(houses);
        }
    }

    public static void main(String[] args) throws URISyntaxException, HttpException, IOException {
        try {
            initializeSpringContext();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
