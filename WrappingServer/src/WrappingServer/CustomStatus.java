package WrappingServer;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.Variant;


public class CustomStatus{
	public static final StatusType NO_CHANGES = new NoChangesStatus();
	public static final StatusType NO_SUCH_USER=new NoSuchUserStatus();
	public static final StatusType DUP_PROJECT= new PersonAlreadyInProjectStatus();
	public static final StatusType DUP_GROUP= new PersonAlreadyInGroupStatus();
	
	
//	public static enum Status extends Enum<Response.Status>
//	implements Response.StatusType{
//		
//	}
	public static class NoChangesStatus implements StatusType{

		@Override
		public Family getFamily() {
			return Family.CLIENT_ERROR;
		}

		@Override
		public String getReasonPhrase() {
			return "No Changes to Previous Data";
		}

		@Override
		public int getStatusCode() {
			return 444;
		}
	}
	public static class NoSuchUserStatus implements StatusType{

		@Override
		public Family getFamily() {
			return Family.CLIENT_ERROR;
		}

		@Override
		public String getReasonPhrase() {
			return "No Such User Exists";
		}

		@Override
		public int getStatusCode() {
			return 420;
		}
	}
	public static class PersonAlreadyInProjectStatus implements StatusType{

		@Override
		public Family getFamily() {
			return Family.CLIENT_ERROR;
		}

		@Override
		public String getReasonPhrase() {
			return "Person is Already in Project";
		}

		@Override
		public int getStatusCode() {
			return 432;
		}
	}
	public static class PersonAlreadyInGroupStatus implements StatusType{

		@Override
		public Family getFamily() {
			return Family.CLIENT_ERROR;
		}

		@Override
		public String getReasonPhrase() {
			return "Person is Already in Group";
		}

		@Override
		public int getStatusCode() {
			return 433;
		}
	}
	public class CustomOutboundResponseBuilder{
		public ResponseBuilder builder=Response.ok().status(NO_CHANGES);
		
	}
//	public static class NoChangesStatus implements StatusType{
//
//		@Override
//		public Family getFamily() {
//			return Family.CLIENT_ERROR;
//		}
//
//		@Override
//		public String getReasonPhrase() {
//			return "No Changes to Previous Data";
//		}
//
//		@Override
//		public int getStatusCode() {
//			return 444;
//		}
//	}
	

}
