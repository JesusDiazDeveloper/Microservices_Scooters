package com.arqui.integrador.initializer;

import org.springframework.stereotype.Component;

import com.arqui.integrador.model.User;
import com.arqui.integrador.repository.IUserRepository;

@Component
public class UserInitializer {
	
    private IUserRepository userRepository;
    
    public UserInitializer(IUserRepository userRepository) {
    	this.userRepository = userRepository;
    }
    
    public void init(){
    	this.userRepository.save(User.builder().name("meste73")
    			.cellphone(2494380393L).email("elmeste.88@gmail.com").firstname("Ezequiel").surname("Mestelan").build());
    	
    	this.userRepository.save(User.builder().name("Frank")
    			.cellphone(2494554466L).email("fdeluccho@gmail.com").firstname("Franco").surname("Delucci").build());
    	
    	this.userRepository.save(User.builder().name("Matt")
    			.cellphone(2494745475L).email("matt.s@gmail.com").firstname("Matias").surname("Sanchez Abrego").build());
    	
        this.userRepository.save(User.builder().name("Jebu")
        		.cellphone(2494332456L).email("eljebu@gmail.com").firstname("Jesus").surname("Diaz").build());
        
        this.userRepository.save(User.builder().name("Carlo")
        		.cellphone(2494252314L).email("elcharly@gmail.com").firstname("Carlos").surname("Garcia").build());
        
        this.userRepository.save(User.builder().name("John")
        		.cellphone(2494313665L).email("john.rambo@gmail.com").firstname("Juan").surname("Rodriguez").build());
    }
}
