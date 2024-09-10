package reviewme.review.service.exception;

import reviewme.global.exception.BadRequestException;

public class SelectedOptionItemCountOutOfRangeException extends BadRequestException {

    public SelectedOptionItemCountOutOfRangeException(long questionId, int selectedCount,
                                                      int minSelectionCount, int maxSelectionCount) {
        super("체크박스 응답 개수가 범위를 벗어났어요. (선택된 개수: %d, 최소 개수: %d, 최대 개수: %d)"
                        .formatted(selectedCount, minSelectionCount, maxSelectionCount),
                "CheckBox answer count out of range - questionId: %d, selectedCount: %d, minSelectionCount: %d, maxSelectionCount: %d"
                        .formatted(questionId, selectedCount, minSelectionCount, maxSelectionCount));
    }
}
