package pt.rest.api.provider;

/**
 * Provider Factory.
 * @author PMorgado
 *
 */
public class ProviderFactory {

	
	/**
	 * Get specific provider implementation.
	 * @param provider
	 * @return
	 */
	public IProvider getProvider(String provider){
		
		if(provider == null){
		   return null;
		}		
		
		ProviderType providerType = ProviderType.valueOf(provider);
		
		try{
			switch (providerType) {
				case GITHUB:
					return new GitHubProvider();
				case BITBUCKET:
					return new BitBucketProvider();
				case GITLAB:
					return new GitLabProvider();
				default:
					return null;
			}
		}catch(Exception e){
		    return null;
		}
	}
}
