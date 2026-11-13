package com.recepsivri.galeri;

import org.springframework.boot.SpringApplication;

public class TestOtoGaleriApplication {

	public static void main(String[] args) {
		SpringApplication.from(OtoGaleriApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
