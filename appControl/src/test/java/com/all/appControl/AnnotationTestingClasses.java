package com.all.appControl;

import com.all.action.ActionType;
import com.all.action.RequestAction;
import com.all.action.ValueAction;
import com.all.event.EventMethod;
import com.all.event.EventType;
import com.all.event.ValueEvent;

public interface AnnotationTestingClasses {
	interface TestTypes {
		String actionId = "actionId";
		String eventId = "eventId";
		String requestId = "requestId";
		String requestFieldId = "requestFieldId";
		ActionType<RequestAction<String, Integer>> request = ActionType.cm(requestId);
		ActionType<RequestAction<Void, String>> requestField = ActionType.cm(requestFieldId);
		ActionType<ValueAction<String>> action = ActionType.cm(actionId);
		EventType<ValueEvent<String>> event = EventType.ev(eventId);
	}

	class RequestMethodTester {
		public boolean requestHandled;

		@RequestMethod(TestTypes.requestId)
		public Integer handleRequest(String number) {
			requestHandled = true;
			return Integer.parseInt(number);
		}
	}

	class RequestFieldTester {
		@SuppressWarnings("unused")
		@RequestField(TestTypes.requestFieldId)
		private String value = "abc";
	}

	class ActionMethodTester {
		public String val = null;

		@ActionMethod(TestTypes.actionId)
		public void handleAction(ValueAction<String> valueAction) {
			val = valueAction.getValue();
		}
	}

	class EventMethodTester {
		public String val = null;

		@EventMethod(TestTypes.eventId)
		public void handleEvent(ValueEvent<String> valueEvent) {
			val = valueEvent.getValue();
		}
	}

	class DualEventTester {
		public String val1 = null;
		public String val2 = null;

		@EventMethod(TestTypes.eventId)
		public void handleEvent1(ValueEvent<String> valueEvent) {
			val1 = valueEvent.getValue();
		}

		@EventMethod(TestTypes.eventId)
		public void handleEvent2(ValueEvent<String> valueEvent) {
			val2 = valueEvent.getValue();
		}
	}

}
