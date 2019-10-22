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

public class Scheduler {

	// EDD
	void generateSchedule(User user) {
		List<Task> tasks = new ArrayList<Task>();
		for (TaskList ls : user.taskLists) {
			tasks.addAll(ls.tasks);
		}
		Collections.sort(tasks, (Task t1, Task t2) -> {
			return getProjectedDate(t1).compareTo(getProjectedDate(t2));
		});
	}

	Date getProjectedDate(Task t) {
		return null;
	}
}
