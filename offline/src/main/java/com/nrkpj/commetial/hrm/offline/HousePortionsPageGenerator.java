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

public class HousePortionsPageGenerator implements PageGenerator<House> {

    private final VelocityEngine velocityEngine;
    private final PageHelper pageHelper;

    public HousePortionsPageGenerator(VelocityEngine velocityEngine, PageHelper pageHelper) {
        this.velocityEngine = velocityEngine;
        this.pageHelper = pageHelper;
    }

    public void generate(Collection<House> houses) throws IOException {
        Template template = velocityEngine.getTemplate("houseportions.html");
        for (House house : houses) {
            File generatedFilesDirectory = new File("generated");
            File file = new File(FilenameUtils.concat(generatedFilesDirectory.getAbsolutePath(), pageHelper.getHousePageName(house)));
            FileWriter writer = new FileWriter(file);
            VelocityContext context = new VelocityContext();
            context.put("portions", pageHelper.getPortions(house));
            context.put("pageHelper", pageHelper);
            context.put("housename", house.getName());
            template.merge(context, writer);
            writer.flush();
            writer.close();
        }
    }

}
