package com.reactivestax.spring5mvc;

import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.validators.DescriptionValidationRule;
import com.reactivestax.spring5mvc.validators.NameValidationRule;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

public class Spring5MvcApplicationJunitTests {

    @Test
    public void testWithCorrectNameAndWrongDescription() {
        //setup
        Widget widget = new Widget();
        Map<String, String> errorMap = new HashMap<>();
        widget.setName("name");
        widget.setDescription("123desc123");
        DescriptionValidationRule descriptionValidationRule = new DescriptionValidationRule();
        NameValidationRule nameValidationRule = new NameValidationRule();

        //actual SUT
        descriptionValidationRule.validate(widget, errorMap);
        nameValidationRule.validate(widget, errorMap);

        //assertions
        Assertions.assertTrue( errorMap.size() == 1,"there has to be one error");
        Assertions.assertTrue( errorMap.get("description") != null,"there should be an error related to description");
        Assertions.assertTrue( StringUtils.startsWith(errorMap.get("description"), "description also has to start with"),"there should be an error related to description");
    }

    @Test
    public void testWithWrongNameAndCorrectDescription() {
        //setup
        Widget widget = new Widget();
        Map<String, String> errorMap = new HashMap<>();
        widget.setName("213name");
        widget.setDescription("desc123");
        DescriptionValidationRule descriptionValidationRule = new DescriptionValidationRule();
        NameValidationRule nameValidationRule = new NameValidationRule();

        //actual SUT
        descriptionValidationRule.validate(widget, errorMap);
        nameValidationRule.validate(widget, errorMap);

        //assertions
        Assertions.assertTrue(errorMap.size() == 1, "there has to be one error");
        Assertions.assertTrue(errorMap.get("name") != null, "there should be an error related to name");
        Assertions.assertTrue(StringUtils.startsWith(errorMap.get("name"), "name also has to start with"), "there should be an error related to name");
    }

    @Test
    public void testWithNameAndDescriptionWrongDescription() {
        //setup
        Widget widget = new Widget();
        Map<String, String> errorMap = new HashMap<>();
        widget.setName("213name");
        widget.setDescription("123desc123");
        DescriptionValidationRule descriptionValidationRule = new DescriptionValidationRule();
        NameValidationRule nameValidationRule = new NameValidationRule();

        //actual SUT
        descriptionValidationRule.validate(widget, errorMap);
        nameValidationRule.validate(widget, errorMap);

        //assertions for name
        Assertions.assertTrue(errorMap.size() == 2, "there has to be one error");
        Assertions.assertTrue(errorMap.get("name") != null, "there should be an error related to name");
        Assertions.assertTrue(StringUtils.startsWith(errorMap.get("name"), "name also has to start with"), "there should be an error related to name");
        //
        //assertions for description

        Assertions.assertTrue(errorMap.get("name") != null, "there should be an error related to name");
        Assertions.assertTrue(StringUtils.startsWith(errorMap.get("name"), "name also has to start with"), "there should be an error related to name");
    }

    @Test
    public void testWithCorrectNameAndDescription() {
        //setup
        Widget widget = new Widget();
        Map<String, String> errorMap = new HashMap<>();
        widget.setName("name123");
        widget.setDescription("desc123");
        DescriptionValidationRule descriptionValidationRule = new DescriptionValidationRule();
        NameValidationRule nameValidationRule = new NameValidationRule();

        //actual SUT
        descriptionValidationRule.validate(widget, errorMap);
        nameValidationRule.validate(widget, errorMap);

        //assertions
        Assertions.assertTrue(errorMap.size() == 0, "there has to be one error");
        //
    }
}
