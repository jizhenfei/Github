package test;

import MVC.Controller;
import MVC.RequestMapping;
import MVC.ModelAndView;

@Controller
public class test {
	@RequestMapping("/hello")
	public ModelAndView hello(ModelAndView mdv) {
		ModelAndView mav = mdv;
		mav.setViewName("test");
		mav.addObject("name", mav.getMap("name"));
		mav.addObject("pas", mav.getMap("pas"));
		return mav;
	}

}
