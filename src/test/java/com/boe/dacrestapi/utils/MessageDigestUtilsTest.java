package com.boe.dacrestapi.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageDigestUtilsTest {

	@Test
	public void md5Same() {
		System.out.println(MessageDigestUtils.md5("hello"));
		assertTrue("输入一样，输出一样", MessageDigestUtils.md5("hello").equals(MessageDigestUtils.md5("hello")));
	}
	
	@Test
	public void md5NotSame() {
		assertFalse("输入不一样，输出不一样", MessageDigestUtils.md5("hello").equals(MessageDigestUtils.md5("hello1")));
	}
}
