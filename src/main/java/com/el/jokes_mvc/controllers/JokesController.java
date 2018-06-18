package com.el.jokes_mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ModelAndView readJokesPage() {
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
		return readJokesPage();
	}
	
	@GetMapping("/delete")
	public ModelAndView createJokeModel(@RequestParam(value="id") int id) {
		Joke joke = jokeRepository.findOne(id);
		if(id < 0 || joke == null) {
			return new ModelAndView("redirect:/read");
		}
		jokeRepository.delete(id);
		return new ModelAndView("redirect:/read");
	}
	
	@GetMapping("/update")
	public ModelAndView updateJokePage(@RequestParam(value="id", defaultValue="-1", required=false) int id) {
		Joke joke = jokeRepository.findOne(id);
		if(id < 0 || joke == null) {
			return new ModelAndView("redirect:/create");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("update");
		mv.addObject("id", id);
		mv.addObject("joke", joke);
		return mv;
	}
	
	@PostMapping("/update")
	public ModelAndView createJokeModel(
			@RequestParam(value="id") int id,
			@RequestParam(value="joke") String joke,
			@RequestParam(value="punchline") String punchline,
			@RequestParam(value="rating") int rating
			) {
		Joke jokePojo = jokeRepository.findOne(id);
		if(jokePojo != null) {
			jokePojo.setJoke(joke);
			jokePojo.setPunchline(punchline);
			jokePojo.setRating(rating);
			jokeRepository.save(jokePojo);
		}
		ModelAndView mv = new ModelAndView("redirect:/read");
		mv.addObject("info", "Updated joke!");
		return mv;
	}

}
