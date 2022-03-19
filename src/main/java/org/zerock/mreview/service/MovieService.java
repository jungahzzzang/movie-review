package org.zerock.mreview.service;

import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    Long register(MovieDTO movieDTO);

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
