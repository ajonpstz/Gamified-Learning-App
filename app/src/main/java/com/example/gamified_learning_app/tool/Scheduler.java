package com.example.gamified_learning_app.tool;

/*
Scheduling problem:

Given N events, each with a deadline, priority, duration, and volaticity, find an optimal scheduling.

The cost of a scheduling is

\sum_{i = 0}^{N-1} (deadline[i] - time[i]) * volaticity[i]

Subjected to:

\sum_{ time[i] == X }  duration[i] < D[X] where D[X] = D[X%7]

Suppose a deadline cannot be met, the default scheduling behavior is to throw out tasks
with lower priority, but increase its priority for the next time it is scheduled.

We can, of course, just binary search incuring a logarithmic complexity.

Constraints:

N <= 10^5
priority[i] <= 5
value[i] <= 100
time[i] <= 365
duration[i], D[i] <= 500 // 8 hours is the maximum you should work on this a day

TL: 1 second
 */

import com.example.gamified_learning_app.data.Task;
import com.example.gamified_learning_app.data.TaskList;
import com.example.gamified_learning_app.data.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

public class Scheduler {
	
	private int MAX_DURATION = 500; // 8 hours a day maximum

	// EDD
	// greedy algorithm that schedules things of higher priority first, each day waiting increases
	// priority of a process by a certain amount
	void generateSchedule(User user) {
		List<Task> tasks = new ArrayList<Task>();
		for (TaskList ls : user.taskLists) {
			tasks.addAll(ls.tasks);
		}
		if (tasks.size() > 0) {
			Collections.sort(tasks, (Task t1, Task t2) -> {
				return t1.expectedDate.compareTo(t2.expectedDate);
			});
			Date today = tasks.get(0).expectedDate;
			PriorityQueue<Task> pq = new PriorityQueue<Task>(1, (Task t1, Task t2) -> {
				return (int) Math.signum(t2.priority - t1.priority);
			});
			for (int taskIndex = 0; taskIndex < tasks.size(); ) {
				if (pq.isEmpty()) {
					today = tasks.get(taskIndex).expectedDate;
					pq.add(tasks.get(taskIndex++));
				}
				while (taskIndex < tasks.size() && tasks.get(taskIndex).equals(today)) {
					pq.add(tasks.get(taskIndex++));
				}
				int currentDuration = 0;
				while (!pq.isEmpty() &&
					pq.peek().computeExpectedDuration() + currentDuration <= MAX_DURATION) {
					pq.poll().nextScheduled = today;
				}
				today.setDate(today.getDay() + 1);
			}
		}
	}
}
