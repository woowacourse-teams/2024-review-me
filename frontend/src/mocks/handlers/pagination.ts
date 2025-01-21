// NOTE: 데이터 타입 T는 반드시 숫자 타입의 ID 필드를 가져야 함
type DataWithNumericId<K extends string, T> = T & Record<K, number>;

interface PaginatedResponse<T> {
  lastDataId: number;
  isLastPage: boolean;
  paginatedDataList: T[];
}

// TODO? 객체 파라미터로 변경?, size 기본 파라미터
/**
 * 데이터를 paginate하는 유틸리티 함수
 *
 * @template K - 데이터 T의 ID 필드의 이름(예: "id", "reviewId")
 * @template T - pagination 대상이 되는 개별 데이터 타입(예: ReviewInfo)
 *
 * @param {DataWithNumericId<K, T>[]} dataList - 페이지 단위로 분할할 데이터 배열
 * @param {number | null} lastDataId - 이전 페이지의 마지막 데이터 ID. 첫 페이지 요청 시 null로 설정됨
 * @param {number} size - 각 페이지에 포함될 데이터 개수
 * @param {K} dataId - 데이터의 id 필드 이름(예: "id", "reviewId")
 *
 * @returns {PaginatedResponse<T>} - 페이지로 분할된 데이터와 메타 정보를 포함하는 객체.
 */
export const paginateDataList = <K extends string, T>(
  dataList: DataWithNumericId<K, T>[], // number형 id를 가진 pagination 대상 데이터 배열
  dataId: K, // 데이터의 id 필드 이름
  lastDataId: number | null,
  size: number,
): PaginatedResponse<T> => {
  const isFirstPage = lastDataId === 0 || lastDataId === null;

  const startIndex = isFirstPage ? 0 : dataList.findIndex((item) => item[dataId] === lastDataId) + 1;

  const endIndex = startIndex + size;

  const paginatedDataList = dataList.slice(startIndex, endIndex);
  const isLastPage = endIndex >= dataList.length;

  return {
    lastDataId: paginatedDataList.length > 0 ? paginatedDataList[paginatedDataList.length - 1][dataId] : 0,
    isLastPage,
    paginatedDataList,
  };
};
