package samples.RiskManagement.DecisionManager;

import java.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class AddDataToList {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		run();
	}

	public static RiskV1UpdatePost201Response run() {
		String type = "negative";
		AddNegativeListRequest requestObj = new AddNegativeListRequest();

		Riskv1liststypeentriesOrderInformation orderInformation = new Riskv1liststypeentriesOrderInformation();
		Riskv1liststypeentriesOrderInformationAddress orderInformationAddress = new Riskv1liststypeentriesOrderInformationAddress();
		orderInformationAddress.address1("1234 Sample St.");
		orderInformationAddress.address2("Mountain View");
		orderInformationAddress.locality("California");
		orderInformationAddress.country("US");
		orderInformationAddress.administrativeArea("CA");
		orderInformationAddress.postalCode("94043");
		orderInformation.address(orderInformationAddress);

		Riskv1liststypeentriesOrderInformationBillTo orderInformationBillTo = new Riskv1liststypeentriesOrderInformationBillTo();
		orderInformationBillTo.firstName("John");
		orderInformationBillTo.lastName("Doe");
		orderInformationBillTo.email("test@example.com");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		Riskv1liststypeentriesPaymentInformation paymentInformation = new Riskv1liststypeentriesPaymentInformation();
		requestObj.paymentInformation(paymentInformation);

		Riskv1liststypeentriesClientReferenceInformation clientReferenceInformation = new Riskv1liststypeentriesClientReferenceInformation();
		clientReferenceInformation.code("54323007");
		Riskv1decisionsClientReferenceInformationPartner clientReferenceInformationPartner = new Riskv1decisionsClientReferenceInformationPartner();
		clientReferenceInformationPartner.developerId("7891234");
		clientReferenceInformationPartner.solutionId("89012345");
		clientReferenceInformation.partner(clientReferenceInformationPartner);

		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1liststypeentriesRiskInformation riskInformation = new Riskv1liststypeentriesRiskInformation();
		Riskv1liststypeentriesRiskInformationMarkingDetails riskInformationMarkingDetails = new Riskv1liststypeentriesRiskInformationMarkingDetails();
		riskInformationMarkingDetails.action("add");
		riskInformation.markingDetails(riskInformationMarkingDetails);

		requestObj.riskInformation(riskInformation);

		RiskV1UpdatePost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			DecisionManagerApi apiInstance = new DecisionManagerApi(apiClient);
			result = apiInstance.addNegative(type, requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			WriteLogAudit(Integer.parseInt(responseCode));
			
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
