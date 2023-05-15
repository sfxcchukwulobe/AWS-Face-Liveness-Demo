import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.CreateFaceLivenessSessionRequest;
import com.amazonaws.services.rekognition.model.CreateFaceLivenessSessionResult;
import com.amazonaws.services.rekognition.model.GetFaceLivenessSessionResultsRequest;
import com.amazonaws.services.rekognition.model.GetFaceLivenessSessionResultsResult;

public class FaceLivenessDetectorApplication {

    static AmazonRekognition rekognitionClient;

    public static void main(String[] args) {
        System.out.println("Welcome to Face Liveness Detector");

        String accessKey = "";
        String secretKey = "";
        String regionName = "";

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        rekognitionClient = AmazonRekognitionClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName(regionName))
                .build();

        try {
            String sessionId = createSession();
            System.out.println("Created a Face Liveness Session with ID: " + sessionId);

            String status = getSessionResults(sessionId);
            System.out.println("Status of Face Liveness Session: " + status);

        } catch(AmazonRekognitionException e) {
            System.out.println("Amazon Rekognition error occurred");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error outside Amazon Rekognition occurred");
            e.printStackTrace();
        }
    }

    private static String createSession() throws Exception {

        CreateFaceLivenessSessionRequest request = new CreateFaceLivenessSessionRequest();
        CreateFaceLivenessSessionResult result = rekognitionClient.createFaceLivenessSession(request);

        String sessionId = result.getSessionId();
        System.out.println("SessionId: " + sessionId);

        return sessionId;
    }

    private static String getSessionResults(String sessionId) throws Exception {

        GetFaceLivenessSessionResultsRequest request = new GetFaceLivenessSessionResultsRequest().withSessionId(sessionId);
        GetFaceLivenessSessionResultsResult result = rekognitionClient.getFaceLivenessSessionResults(request);

        Float confidence = result.getConfidence();
        String status = result.getStatus();

        System.out.println("Confidence: " + confidence);
        System.out.println("status: " + status);

        return status;
    }

}
