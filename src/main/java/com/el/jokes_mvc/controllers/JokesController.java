package com.el.jokes_mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.el.jokes_mvc.models.Joke;
import com.el.jokes_mvc.repositories.JokeRepository;

@Controller
public class JokesController {
	
	@Autowired
	private JokeRepository jokeRepository;

	public JokesController() {
		
	}
	
	@GetMapping("/")
	public ModelAndView index() {
		return new ModelAndView("redirect:/read");
	}
	
	@GetMapping("/read")
	public ModelAndView readJokes() {
		ModelAndView mv = new ModelAndView();
		List<Joke> jokes = jokeRepository.findAll();
		mv.addObject("jokes", jokes);
		mv.setViewName("read");
		return mv;
	}
	
	@GetMapping("/create")
	public ModelAndView createJokePage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("create");
		return mv;
	}
	
	@PostMapping("/create")
	public ModelAndView createJokeModel(
			@RequestParam(value="joke") String joke,
			@RequestParam(value="punchline") String punchline,
			@RequestParam(value="rating") int rating) {
		jokeRepository.save(new Joke(joke, punchline, rating));
		return readJokes();
	}
	
	@GetMapping("/delete")
	public ModelAndView createJokeModel(@RequestParam(value="id") int id) {
		jokeRepository.delete(id);
		return readJokes();
	}

}
