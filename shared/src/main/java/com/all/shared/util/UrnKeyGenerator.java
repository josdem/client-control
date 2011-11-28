package com.all.shared.util;

import java.net.URI;
import java.util.UUID;

public class UrnKeyGenerator {

//	private static final Log log = LogFactory.getLog(UrnKeyGenerator.class);


  /**
   * This defines the URI scheme that we will be using to present IDs.
   * IDs are encoded for presentation into URIs (see
   * {@link <a href="http://www.ietf.org/rfc/rfc2396.txt">IETF RFC 2396 Uniform Resource Identifiers (URI) : Generic Syntax</a>}
   * ) as URNs (see
   * {@link <a href="http://www.ietf.org/rfc/rfc2141.txt">IETF RFC 2141 Uniform Resource Names (URN) Syntax</a>}
   * ).
   */
  public static final String URIEncodingName = "urn";

  /**
   *  This defines the URN Namespace that we will be using to present IDs.
   *  The namespace allows URN resolvers to determine which sub-resolver to use
   *  to resolve URN references. All IDs are presented in this namespace.
   */
  //TODO: Define this nameSpace 
  public static final String URNNamespace = "all";
	
  public static URI generateUrn(){
		URI uri = URI.create(URIEncodingName + ":" + URNNamespace + ":uuid-" + UUID.randomUUID());
		return uri;
	}

}