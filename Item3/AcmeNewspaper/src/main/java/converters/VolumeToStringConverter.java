
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Volume;

@Component
@Transactional
public class VolumeToStringConverter implements Converter<Volume, String> {

	@Override
	public String convert(final Volume volume) {
		String result;

		if (volume == null)
			result = null;
		else
			result = String.valueOf(volume.getId());

		return result;
	}

}
