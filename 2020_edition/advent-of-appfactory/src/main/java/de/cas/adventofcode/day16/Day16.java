package de.cas.adventofcode.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.ListUtils;

public class Day16 extends Day<Long> {
	protected Day16() {
		super(16);
	}

	public static void main(final String[] args) {
		new Day16().run();
	}

	@Override
	public Long solvePart1(List<String> input) {
		input.add("");
		List<TicketRule> ticketRules = parseTicketRules(input);
		List<List<Integer>> nearbyTickets = parseTickets(input, "nearby tickets:");
		return Long.valueOf(nearbyTickets.stream().flatMapToInt(ticket -> getInvalidValues(ticketRules, ticket)).sum());
	}
	
	private IntStream getInvalidValues(List<TicketRule> ticketRules, List<Integer> ticket) {
		return ticket.stream()
				.filter(value -> findMatchingRules(ticketRules, List.of(value)).size() == 0)
				.mapToInt(i -> i);
	}
	
	private Set<TicketRule> findMatchingRules(List<TicketRule> ticketRules, List<Integer> values) {
		return ticketRules.stream()
				.filter(rule -> values.stream().allMatch(value -> rule.isValid(value)))
				.collect(Collectors.toSet());
	}

	@Override
	public Long solvePart2(List<String> input) {
		input.add("");
		List<TicketRule> ticketRules = parseTicketRules(input);
		List<Integer> yourTicket = parseTickets(input, "your ticket:").get(0);
		List<List<Integer>> nearbyTickets = parseTickets(input, "nearby tickets:");
		List<List<Integer>> validTickets = nearbyTickets.stream()
				.filter(ticket -> getInvalidValues(ticketRules, ticket).count() == 0)
				.collect(Collectors.toList());
		List<TicketRule> orderedRules = orderRules2(ticketRules, validTickets);
		List<Integer> indices = getDepartureIndices(orderedRules);
		return indices.stream().mapToLong(yourTicket::get).reduce((a, b) -> a * b).getAsLong();
	}

	private List<Integer> getDepartureIndices(List<TicketRule> orderedRules) {
		List<Integer> indices = new ArrayList<>();
		for(int i = 0; i < orderedRules.size(); i++) {
			if (orderedRules.get(i).getName().startsWith("departure")) {
				indices.add(i);
			}
		}
		return indices;
	}
	
	public List<TicketRule> orderRules2(List<TicketRule> ticketRules, List<List<Integer>> tickets) {
		List<List<Integer>> ticketColumns = ListUtils.getColumns(tickets);
		List<Set<TicketRule>> validRuleSetPerColumn = ticketColumns.stream()
				.map(col -> findMatchingRules(ticketRules, col))
				.collect(Collectors.toList());
		Set<TicketRule> determinedRules = new HashSet<>();
		
		do {
			validRuleSetPerColumn.stream()
			.filter(ruleSet -> ruleSet.size() == 1)
			.forEach(determinedRules::addAll);
			
			validRuleSetPerColumn.stream()
			.filter(ruleSet -> ruleSet.size() > 1)
			.forEach(ruleSet -> ruleSet.removeAll(determinedRules));
		} while (determinedRules.size() != ticketRules.size());
		
		return validRuleSetPerColumn.stream()
				.flatMap(Set::stream)
				.collect(Collectors.toList());
	}

	public List<TicketRule> orderRules(List<TicketRule> ticketRules, List<List<Integer>> tickets, int i) {
		List<TicketRule> orderedRules = new ArrayList<>();
		if (i == tickets.get(0).size())
			return orderedRules;
		
		for(TicketRule ticketRule : ticketRules) {
			boolean ruleIsValid = tickets.stream().mapToInt(ticket -> ticket.get(i)).allMatch(ticketRule::isValid);

			if(!ruleIsValid) {
				continue;
			}

			orderedRules = new ArrayList<>(List.of(ticketRule));

			List<TicketRule> ticketRulesExceptCurrentRule = new ArrayList<>(ticketRules);
			ticketRulesExceptCurrentRule.remove(ticketRule);
			List<TicketRule> orderedSubRules = orderRules(ticketRulesExceptCurrentRule, tickets, i + 1);

			if((i+1) != tickets.get(0).size() && orderedSubRules.isEmpty()) {
				orderedRules.clear();
				continue;
			}

			orderedRules.addAll(orderedSubRules);
			return orderedRules;
		}
		
		return orderedRules;
	}

	private List<TicketRule> parseTicketRules(List<String> input) {
		List<TicketRule> ticketRules = new ArrayList<>();
		for (String line : input) {
			if (line.equalsIgnoreCase("")) {
				break;
			}

			ticketRules.add(TicketRule.fromLine(line));
		}
		return ticketRules;
	}

	private List<List<Integer>> parseTickets(List<String> input, String startPattern) {
		List<List<Integer>> tickets = new ArrayList<>();
		boolean started = false;
		for (String line : input) {
			if (line.equalsIgnoreCase(startPattern)) {
				started = true;
				continue;
			}

			if (started && line.equalsIgnoreCase("")) {
				break;
			}

			if (started) {
				tickets.add(Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
			}
		}
		return tickets;
	}
}
