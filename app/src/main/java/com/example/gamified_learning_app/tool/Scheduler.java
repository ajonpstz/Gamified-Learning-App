package com.example.gamified_learning_app.tool;

import com.example.gamified_learning_app.data.Task;
import com.example.gamified_learning_app.data.TaskList;
import com.example.gamified_learning_app.data.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.time.temporal.ChronoUnit.DAYS;

public class Scheduler {
	
	private int MAX_DURATION = 500; // 8 hours a day maximum
	private int MAX_SCHEDULE_AHEAD = 30; // Schedule 30 days ahead

	// EDD
	// greedy algorithm that schedules things of higher priority first, each day waiting increases
	// priority of a process by a certain amount
	void generateSchedule(User user) {
		List<Task> tasks = new ArrayList<Task>();
		Map<Date,Integer> map = new TreeMap<>();
		for (TaskList ls : user.taskLists) {
			for (Task t : ls.tasks) {
				if (DAYS.between(t.computeExpectedDate().toInstant(), new Date().toInstant())
					<= MAX_SCHEDULE_AHEAD) {
					if (t.dateScheduled == null) {
						tasks.add(t);
					} else {
						if (!map.containsKey(t.dateScheduled))
							map.put(t.dateScheduled, 0);
						map.put(t.dateScheduled, map.get(t.dateScheduled) +
							t.computeExpectedDuration());
					}
				}
			}
		}
		Collections.sort(tasks, (Task t1, Task t2) -> {
			if (t1.priority > t2.priority)
				return 1;
			else
				return t1.priority == t2.priority ? 0 : -1;
		});
		Collections.sort(tasks, (Task t1, Task t2) -> {
			return t1.computeExpectedDate().compareTo(t2.computeExpectedDate());
		});
		int i = 0;
		Date current = new Date();
		LinkedList<Task> toSchedule = new LinkedList<Task>();
		for (; i < tasks.size() || toSchedule.size() > 0; LocalDateTime.from(current.toInstant()).plusDays(1)) {
			int minutes = MAX_DURATION;
			if (map.containsKey(current)) {
				minutes -= map.get(current);
			}
			
			while (i < tasks.size() && !tasks.get(i).computeExpectedDate().after(current)) {
				toSchedule.addLast(tasks.get(i++));
			}
			
			int sz = toSchedule.size();
			while (sz-->0) {
				if ( toSchedule.get(0).computeExpectedDuration() <= minutes ) {
					toSchedule.get(0).dateScheduled = current;
					minutes -= toSchedule.get(0).computeExpectedDuration();
					toSchedule.removeFirst();
				} else {
					toSchedule.addLast(toSchedule.removeFirst());
				}
			}
		}
	}
}
