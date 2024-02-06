package openAI.demo.domain.chat;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class ChatController {

    @GetMapping("/chat")
    public String chat() {
        return "/api/chat";
    }

    @PostMapping("/chat")
    @ResponseBody
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");

        // OpenAI API 호출 및 응답 처리
        String aiReply = callOpenAPI(userMessage);

        Map<String, String> response = new HashMap<>();
        response.put("reply", aiReply);
        return response;
    }

    private String callOpenAPI(String userMessage) {
        // OpenAI API 호출 및 응답 처리 로직을 구현하세요.
        // 필요한 인증 정보, HTTP 요청 등을 사용하여 OpenAI API에 요청을 보내고 응답을 처리합니다.
        // 이 예시에서는 OpenAI API 호출 과정은 생략되었습니다.
        String apiKey = "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"; // OpenAI API 키
        String endpoint = "https://api.openai.com/v1/chat/completions"; // OpenAI API 엔드포인트

        // OpenAI API 호출에 필요한 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // OpenAI API 호출에 필요한 요청 본문 설정
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", Arrays.asList(Map.of("role", "system", "content", "You are a helpful assistant.")));
        requestBody.put("messages", Arrays.asList(Map.of("role", "user", "content", userMessage)));
        requestBody.put("max_tokens", 1000);

        // OpenAI API 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, Map.class);

        // OpenAI API 응답 처리
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = responseEntity.getBody();
            assert responseBody != null;
            ArrayList<LinkedHashMap<String, String>> choicesList = (ArrayList<LinkedHashMap<String, String>>) responseBody.get("choices");
            LinkedHashMap<String, String> s = choicesList.get(0);
            Object s1 = s.values().stream().collect(Collectors.toList());
            // List에서 두 번째 요소 가져오기 (LinkedHashMap)
            String result = ((LinkedHashMap) ((List<?>) s1).get(1)).get("content").toString();
// LinkedHashMap에서 "key" 키에 해당하는 값 가져오기
//            String value = map.get("content");
//            StringBuilder resultBuilder = new StringBuilder();
//            for (Map<String, String> choice : choicesList) {
//                String text = choice.get("message");
//                resultBuilder.append(text);
//            }
//            return resultBuilder.toString();
            return result;
        } else {
            throw new RuntimeException("Failed to call OpenAI API");
        }
    }
}
