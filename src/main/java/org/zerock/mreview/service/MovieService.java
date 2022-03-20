package org.zerock.mreview.service;

import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    //등록
    Long register(MovieDTO movieDTO);

    //목록 처리
    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    //조회 처리
    MovieDTO getMovie(Long mno);

                                        //여러 개의 이미지를 처리하기 위해 List로 받음,  Double 타입의 평점 평균, Long 타입의 리뷰 개수
    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt){
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder().imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }

    /*
    Movie를 JPA로 처리하기 위해서는 MovieDTO > Movie 객체로 변환 필요
    이미지 객체들도 같이 처리 필요 -> 한 번에 두 가지 종류의 객체를 반환해야 하므로
    Map 타입을 이용해서 반환함.
     */
    default Map<String,Object> dtoToEntity(MovieDTO movieDTO){
        Map<String,Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();
        entityMap.put("movie",movie);
        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
        //MovieImageDTO 처리
        if(imageDTOList != null && imageDTOList.size()>0){
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {
                MovieImage movieImage = MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie).build();
                return movieImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList",movieImageList);
        }
        return entityMap;
    }

}
