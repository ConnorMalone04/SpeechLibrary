package Project1.SpeechLibrary.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import Project1.SpeechLibrary.data.TopicRepository;
import Project1.SpeechLibrary.model.Topic;

@Component
public class StringToTopicConverter implements Converter<String, Topic> {

    private final TopicRepository topicRepository;

    public StringToTopicConverter(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Topic convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }

        return topicRepository.findById(Long.valueOf(source))
                .orElse(null);
    }
}
