package com.demo.captcha;

import java.awt.Color;
import java.util.Locale;
import java.util.Random;

import com.github.cage.Cage;
import com.github.cage.image.ConstantColorGenerator;
import com.github.cage.image.EffectConfig;
import com.github.cage.image.Painter;
import com.github.cage.image.ScaleConfig;
import com.github.cage.token.RandomCharacterGeneratorFactory;
import com.github.cage.token.RandomTokenGenerator;

public class DCage extends Cage {

	/**
	 * Height of CAPTCHA image.
	 */
	protected static final int HEIGHT = 80;

	/**
	 * Width of CAPTCHA image.
	 */
	protected static final int WIDTH = 290;

	/**
	 * Character set supplied to the {@link RandomTokenGenerator} used by this template.
	 */
	protected static final char[] TOKEN_DEFAULT_CHARACTER_SET = (new String(RandomCharacterGeneratorFactory.DEFAULT_DEFAULT_CHARACTER_SET).replaceAll("b|f|i|j|l|m|o|t", "")
			+ new String(RandomCharacterGeneratorFactory.DEFAULT_DEFAULT_CHARACTER_SET).replaceAll("c|i|o", "").toUpperCase(Locale.ENGLISH)
			+ new String(RandomCharacterGeneratorFactory.ARABIC_NUMERALS).replaceAll("0|1|9", "")).toCharArray();

	/**
	 * Minimum length of token.
	 */
	protected static final int TOKEN_LEN_MIN = 6;

	/**
	 * Maximum length of token is {@value #TOKEN_LEN_MIN} + {@value #TOKEN_LEN_DELTA}.
	 */
	protected static final int TOKEN_LEN_DELTA = 0;

	/**
	 * Constructor.
	 */
	public DCage() {
		this(new Random());
	}

	/**
	 * Constructor.
	 * 
	 * @param rnd object used for random value generation. Not null.
	 */
	protected DCage(Random rnd) {
		super(new Painter(WIDTH, HEIGHT, null, null, new EffectConfig(true, true, true, false, new ScaleConfig(0.55f, 0.55f)), rnd), null, new ConstantColorGenerator(Color.BLACK),
				null, Cage.DEFAULT_COMPRESS_RATIO,
				new RandomTokenGenerator(rnd, new RandomCharacterGeneratorFactory(TOKEN_DEFAULT_CHARACTER_SET, null, rnd), TOKEN_LEN_MIN, TOKEN_LEN_DELTA), rnd);
	}
}
