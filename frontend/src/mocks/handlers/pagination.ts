import { DEFAULT_SIZE_PER_PAGE } from '@/constants';

type DataWithNumericId<K extends string, T> = T & Record<K, number>;

interface PaginatedResponse<T> {
  lastDataId: number;
  isLastPage: boolean;
  paginatedDataList: T[];
}

interface PaginateParams<K extends string, T> {
  dataList: DataWithNumericId<K, T>[]; // 페이지네이션할 데이터 배열
  dataId: K; // 데이터의 ID 필드 이름 (예: "id", "reviewId")
  lastDataId?: number | null; // 이전 페이지의 마지막 ID (첫 요청 시 null)
  size?: number; // 페이지 크기 
}

/**
 * 데이터를 paginate하는 유틸리티 함수
 *
 * @template K - 데이터 T의 ID 필드의 이름(예: "id", "reviewId")
 * @template T - pagination 대상이 되는 개별 데이터 타입(예: ReviewInfo)
 *
 * @param {PaginateParams<K, T>} params - 페이지네이션을 위한 파라미터 객체
 * @returns {PaginatedResponse<T>} - 페이지로 분할된 데이터와 메타 정보를 포함하는 객체.
 */
export const paginateDataList = <K extends string, T>({
  dataList,
  dataId,
  lastDataId = null, // 기본값 설정
  size = DEFAULT_SIZE_PER_PAGE,
}: PaginateParams<K, T>): PaginatedResponse<T> => {
  const isFirstPage = lastDataId === 0 || lastDataId === null;

  // lastDataId 이후의 데이터를 찾기
  const startIndex = isFirstPage ? 0 : dataList.findIndex((item) => item[dataId] === lastDataId) + 1;

  // size만큼 데이터 추출
  const endIndex = startIndex + size;
  const paginatedDataList = dataList.slice(startIndex, endIndex);
  const isLastPage = endIndex >= dataList.length;

  return {
    lastDataId: paginatedDataList.length > 0 ? paginatedDataList[paginatedDataList.length - 1][dataId] : 0,
    isLastPage,
    paginatedDataList,
  };
};
