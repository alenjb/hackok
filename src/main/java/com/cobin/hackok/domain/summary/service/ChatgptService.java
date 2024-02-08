package com.cobin.hackok.domain.summary.service;

import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.dto.ChatResponse;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatRequest;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatResponse;

import java.util.List;

public interface ChatgptService {

    String sendMessage(String message);

    // chat gpt에게 요청

    ChatResponse sendChatRequest(ChatRequest request);

    String multiChat(List<MultiChatMessage> messages);

    MultiChatResponse multiChatResponse(MultiChatRequest multiChatRequest);
}
