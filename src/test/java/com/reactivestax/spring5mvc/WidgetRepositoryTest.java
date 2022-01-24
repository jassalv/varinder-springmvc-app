package com.reactivestax.spring5mvc;

import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.repository.WidgetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@Transactional  -- no need to add this @DataJpaTest does it automatically for you
public class WidgetRepositoryTest {

	@Autowired
	WidgetRepository widgetRepository;


	@Test
	public void getWidgetReturnWidgetDetails(){

		Widget savedWidget1 = widgetRepository.save(new Widget("name6677", "description6677"));
		Widget savedWidget2 = widgetRepository.save(new Widget("name6678", "description6678"));


		Optional<Widget> widgetById = widgetRepository.findById(savedWidget1.getId());
		Optional<Widget> widgetById2 = widgetRepository.findById(savedWidget2.getId());

		Assertions.assertThat(widgetById).isPresent();
		Assertions.assertThat(widgetById2).isPresent();

	}
}
