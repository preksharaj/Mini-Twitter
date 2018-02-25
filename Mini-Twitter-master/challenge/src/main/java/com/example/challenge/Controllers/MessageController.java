/**
* Controller to handle messages sent by User
* @author Preksha Raj
*/

package com.example.challenge.Controllers;

import com.example.challenge.DAO.MessageDAO;
import com.example.challenge.Service.MessageService;
import com.example.challenge.VO.People;
import com.example.challenge.VO.Followers;
import com.example.challenge.VO.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {

    
    @Autowired
    MessageService messageService;


	/**
	* Ruote for seaching of mesaages based on keywords
	* @return Filtered list of messages
	*/
	@RequestMapping(value="/message/search={keyword}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Messages> searchMessages(@PathVariable String keyword) {
		return messageService.searchMessages(keyword);
	}
    
    /**
	* Ruote for getting mesaages sent by user and the people followed by the user
	* @return list of messages
	*/
    @RequestMapping(value="/message", method = RequestMethod.GET,produces="application/json")
    public List<Messages> getMessages(@RequestParam("search") Optional<String> keyword){
            return messageService.getMessages(keyword);
    }
}
