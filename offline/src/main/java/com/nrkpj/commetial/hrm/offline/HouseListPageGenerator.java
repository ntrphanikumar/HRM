package com.nrkpj.commetial.hrm.offline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.nrkpj.commetial.hrm.core.dos.House;

public class HouseListPageGenerator implements PageGenerator<House> {

    private final VelocityEngine velocityEngine;
    private final PageHelper pageHelper;

    public HouseListPageGenerator(VelocityEngine velocityEngine, PageHelper pageHelper) {
        this.velocityEngine = velocityEngine;
        this.pageHelper = pageHelper;
    }

    public void generate(Collection<House> houses) throws IOException {
        File generatedFilesDirectory = new File("generated");
        File file = new File(FilenameUtils.concat(generatedFilesDirectory.getAbsolutePath(), "index.html"));
        Template template = velocityEngine.getTemplate("houseslist.html");
        FileWriter writer = new FileWriter(file);
        VelocityContext context = new VelocityContext();
        context.put("houses", houses);
        context.put("pageHelper", pageHelper);
        template.merge(context, writer);
        writer.flush();
        writer.close();
    }
}
