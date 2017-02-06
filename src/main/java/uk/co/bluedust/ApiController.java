package uk.co.bluedust;

import io.swagger.annotations.ApiOperation;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO: Auto-generated Javadoc

/**
 * The Class ApiController.
 */
@RequestMapping("/api")
@RestController
public class ApiController {

	/**
	 * Gets the uuid.
	 *
	 * @param isUrlFriendly
	 *            the is url friendly
	 * @return the uuid
	 */
	@RequestMapping(value = "/uuid", method = RequestMethod.GET)
	@ApiOperation(value = "provides a UUID", notes = "universally unique identifiers based on the OOTB Java implementation", response = String.class)
	public String getUuid(
			@RequestParam(required = false, defaultValue = "true") Boolean isUrlFriendly) {

		String uuid = UUID.randomUUID().toString();
		return Boolean.TRUE.equals(isUrlFriendly) ? uuid.replaceAll("-", "")
				: uuid;

	}

	@RequestMapping(value = "/random", method = RequestMethod.GET)
	@ApiOperation(value = "provides a random integer", notes = "random num generator between min a max", response = Integer.class)
	public Integer getRandomeNumber(
			@RequestParam(required = false, defaultValue = "0") int min,
			@RequestParam(required = false, defaultValue = "10") int max) {
		
		if (min>=max) {
			throw new IllegalArgumentException(String.format("min(%s) must be smaller than max(%s)", min, max));
		}

		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
}
