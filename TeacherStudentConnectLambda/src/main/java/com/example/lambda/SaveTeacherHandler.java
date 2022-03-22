package com.example.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.entity.Teacher;
import com.example.repository.TeacherRepository;
import com.google.gson.Gson;

public class SaveTeacherHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		
		if (input.getBody() != null) {
			Teacher newTeacherObj = new Gson().fromJson(input.getBody(), Teacher.class);
			System.out.println("Input received:"+newTeacherObj.toString());
			TeacherRepository repo = new TeacherRepository();
			newTeacherObj = repo.saveTeacher(newTeacherObj);
		
		
			APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();	    
			responseEvent.setStatusCode(200);
			responseEvent.setBody(new Gson().toJson(newTeacherObj));  
			return responseEvent;
		}
		return null;
	}

}
