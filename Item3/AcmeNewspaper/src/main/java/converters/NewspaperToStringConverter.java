
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Newspaper;

@Component
@Transactional
public class NewspaperToStringConverter implements Converter<Newspaper, String> {

	@Override
	public String convert(final Newspaper newspaper) {
		String result;

		if (newspaper == null)
			result = null;
		else
			result = String.valueOf(newspaper.getId());

		return result;
	}

}
