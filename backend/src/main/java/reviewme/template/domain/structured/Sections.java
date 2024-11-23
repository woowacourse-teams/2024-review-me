package reviewme.template.domain.structured;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import reviewme.template.domain.Section;
import reviewme.template.domain.exception.SectionNotExistException;

public class Sections {

    private final List<Section> sections;

    public Sections(List<Section> sections) {
        validateSections(sections);
        this.sections = sections.stream()
                .distinct()
                .toList();
    }

    private void validateSections(List<Section> sections) {
        if (sections == null || sections.isEmpty()) {
            throw new SectionNotExistException();
        }
    }

    public List<Long> getSectionIds() {
        return sections.stream()
                .map(Section::getId)
                .toList();
    }

    public Set<Long> getQuestionIds() {
        return sections.stream()
                .flatMap(section -> section.getQuestionIds().stream())
                .collect(Collectors.toSet());
    }

    public List<Long> getInvisibleSectionIds(List<Long> selectedOptionIds) {
        return sections.stream()
                .filter(section -> !section.isVisibleBySelectedOptionIds(selectedOptionIds))
                .map(Section::getId)
                .toList();
    }

    public List<Long> getInvisibleSectionIds() {
        return sections.stream()
                .filter(section -> !section.isAlwaysVisible())
                .map(Section::getId)
                .toList();
    }
}
