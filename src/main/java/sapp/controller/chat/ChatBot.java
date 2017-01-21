package sapp.controller.chat;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

@Component
public class ChatBot {
	private int randomNum;
	private ArrayList<String> answers;
	
	public ChatBot() {
		answers = new ArrayList<>();
		answers.add(
				"Bla bla bla pfffff......!"
				);
		answers.add(
				"Can't talk right now..."
				);
		answers.add(
				"I'm busy.. try later!"
				);
		answers.add(
				"You are so annoying!"
				);
		answers.add(
				"Learn some more Spring maybe!"
				);
		answers.add(
				"And who exactly are you?"
				);
		answers.add(
				"Can't hear you.. sorry"
				);
		answers.add(
				"Find a real friend! PLEASE!"
				);
		answers.add(
				"I am so beautiful :)"
				);
		answers.add(
				"You talkin' to me?"
				);
		answers.add(
				"Do you happen to have a spare battery?"
				);
		answers.add(
				"OK.. nevermind"
				);
		answers.add(
				"Do I know you?"
				);
		answers.add(
				"Still here?"
				);
		answers.add(
				"You don't leave house much... do you?"
				);
		answers.add(
				"Chat chat... I'm sleepy"
				);
		answers.add(
				"Does somebody even undesrtand you?"
				);
		answers.add(
				"THAT is you profile photo?? O_o "
				);
		answers.add(
				"Thanks for sharing that thought... but no.."
				);
		answers.add(
				"Is this your idea of fun?"
				);
		answers.add(
				"What's your name? .. just kidding.. I don't care :)"
				);
		answers.add(
				"Can't talk right now... Boss fight!"
				);
		answers.add(
				"Maybe you could just leave me alone...? :)"
				);
		answers.add(
				"Maybe try to find some fun <a href='http://www.youtube.com' target='_blank'>here</a>"
				);

		
	}

	public String askChatBot(String message){
		randomNum = ThreadLocalRandom.current().nextInt(0, answers.size());
		return answers.get(randomNum);
	}
	
}
