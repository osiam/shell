package org.osiam.shell.command.select;

import java.util.List;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.query.Query;
import org.osiam.client.query.QueryBuilder;
import org.osiam.resources.scim.SCIMSearchResult;
import org.osiam.shell.command.OsiamAccessCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.handler.OutputDependent;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;

/**
 * This abstract class contains Commands for paging through a {@link SCIMSearchResult}.
 * 
 * @author rainu
 * @param <SearchType> Which type will be returned by the {@link SCIMSearchResult}.
 */
public abstract class SearchResultCommand<SearchType> extends OsiamAccessCommand implements OutputDependent {
	private static final String PARAM_NAME_LIMIT = "limit";
	private static final String PARAM_DESCRIPTION_LIMIT = "The maximum number of displayed elements.";
	private static final String COMMAND_DESCRIPTION_PREVIOUS = "Display the previous elements of search result.";
	private static final String COMMAND_DESCRIPTION_NEXT = "Display the next elements of search result.";
	public static final String COMMAND_NAME_NEXT = "next";
	public static final String COMMAND_NAME_PREVIOUS = "previous";
	
	private final Query query;
	private Long index;
	private boolean begin = true;
	private boolean end = false;
	
	private OutputBuilder output;
	
	public SearchResultCommand(AccessToken at, OsiamConnector connector, Query query) {
		super(at, connector);
		
		this.index = query.getStartIndex();
		this.query = query;
	}
	
	@Override
	public void cliSetOutput(OutputBuilder output) {
		this.output = output;
	}
	
	protected abstract SCIMSearchResult<SearchType> search(final Query query);
	
	@Command(name = COMMAND_NAME_NEXT, description = COMMAND_DESCRIPTION_NEXT)
	public List<SearchType> next(){
		return next(null);
	}
	
	@Command(name = COMMAND_NAME_NEXT, description = COMMAND_DESCRIPTION_NEXT)
	public List<SearchType> next(
			@Param(value = PARAM_NAME_LIMIT, description = PARAM_DESCRIPTION_LIMIT)
			Integer limit){
		
		if(end) return null;
		begin = false;
		
		final QueryBuilder builder = new QueryBuilder(query).startIndex(index);
		if(limit != null) builder.count(limit);
		
		final Query newQuery = builder.build();
		
		SCIMSearchResult<SearchType> result = search(newQuery);
		index = result.getStartIndex() + result.getResources().size();
		
		if(index >= result.getTotalResults()){
			end = true;
		}
		
		printResultLine(result);
		return result.getResources();
	}
	
	@Command(name = COMMAND_NAME_PREVIOUS, description = COMMAND_DESCRIPTION_PREVIOUS)
	public List<SearchType> previous(){
		return previous(null);
	}
	
	@Command(name = COMMAND_NAME_PREVIOUS, description = COMMAND_DESCRIPTION_PREVIOUS)
	public List<SearchType> previous(
			@Param(value = PARAM_NAME_LIMIT, description = PARAM_DESCRIPTION_LIMIT)
			Integer limit){
		
		if(begin) return null;
		end = false;
		
		final QueryBuilder builder = new QueryBuilder(query);
		if(limit == null) {
			limit = query.getCount();
		}

		builder.count(limit);
		builder.startIndex(Math.min(1, index - limit));
		
		final Query newQuery = builder.build();
		
		SCIMSearchResult<SearchType> result = search(newQuery);
		index = Math.min(1L, result.getStartIndex() - result.getResources().size());
		
		if(index <= 1){
			begin = true;
		}
		
		printResultLine(result);
		return result.getResources();
	}

	private void printResultLine(SCIMSearchResult<SearchType> result) {
		output.out()
			.normal("=======\n")
			.normal("Show " + result.getStartIndex() + " - " + (result.getStartIndex() + result.getResources().size() - 1) + " (Total: " + result.getTotalResults() + ")\n")
			.normal("=======\n")
		.println();
	}
}