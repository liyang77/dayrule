/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuseinfo.dayrule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EasterDateRule implements BusinessDayRule {
	int[] adjs;
	
	public EasterDateRule(String[] args) {
		List<Integer> adjList = new ArrayList<Integer>();
		for (final String arg: args) {
			adjList.add(Integer.parseInt(arg));
		}
		adjs = new int[adjList.size()];
		Iterator<Integer> itAdjs = adjList.iterator();
		for (int i = 0; i < adjs.length; i++) {
			adjs[i] = itAdjs.next();
		}
	}
	
	@Override
	public Set<Integer> getAll(int y) {
		Set<Integer> res = new HashSet<Integer>();
		int md = getDate(y);
		int n = md >> 5;
		int p = md & 31;
		for (int adj: adjs) {
	    	int[] ymd = DateUtils.adjDays(y, n, p + adj);
			res.add((ymd[1] << 5) + ymd[2]);
		}
		return res;
	}
	
	private int getDate(int y) {
		int a = y % 19;
		int b = y / 100;
		int c = y % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = (19 * a + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + 2 * e + 2 * i - h - k) % 7;
		int m = (a + 11 * h + 22 * l) / 451;
		int j = h + l - 7 * m + 114;
		int n = j / 31;
		int p = j % 31 + 1;
		return (n<<5) + p;
	}

	@Override
	public boolean apply(int y, int m, int d) {
		int md = getDate(y);
		int n = md >> 5;
		int p = md & 31;
		for (int adj: adjs) {
	    	int[] ymd = DateUtils.adjDays(y, n, p + adj);
	    	if (m == ymd[1] && d == ymd[2]) {
	    		return true;
	    	}
		}
		return false;
	}

}
