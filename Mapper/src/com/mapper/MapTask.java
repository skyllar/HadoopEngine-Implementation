package com.mapper;

public class MapTask implements IMapper {

	@Override
	public String map(String input) {
		if (input.length() > 2)
			input = input.substring(1);
		return input;
	}

}
