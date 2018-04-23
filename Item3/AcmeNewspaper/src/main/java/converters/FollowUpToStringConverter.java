
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FollowUp;

@Component
@Transactional
public class FollowUpToStringConverter implements Converter<FollowUp, String> {

	@Override
	public String convert(final FollowUp followUp) {
		String result;

		if (followUp == null)
			result = null;
		else
			result = String.valueOf(followUp.getId());

		return result;
	}

}
