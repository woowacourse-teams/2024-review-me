package reviewme.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.template.service.dto.response.SectionNamesResponse;

@Service
@RequiredArgsConstructor
public class SectionService {

    @Transactional(readOnly = true)
    public SectionNamesResponse getSectionNames(String reviewRequestCode) {
        return null;
    }
}
