package com.curso.salareuniao.controller;

import com.curso.salareuniao.exception.ResourceNotFoundException;
import com.curso.salareuniao.model.Room;
import com.curso.salareuniao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/id")
    public ResponseEntity<Room> getRoomById(@PathVariable(value = "id") long roomID)
            throws ResourceNotFoundException {
        Room room = getRoom(roomID);
        return ResponseEntity.ok().body(room);
    }

    private Room getRoom(long roomID) throws ResourceNotFoundException {
        return roomRepository.findById(roomID)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found ::" + roomID));
    }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room room) {
        return roomRepository.save(room);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value = "id") Long roomId,
                                           @Valid @RequestBody Room roomDetails) throws ResourceNotFoundException {
        Room room = getRoom(roomId);
        room.setName(roomDetails.getName());
        room.setDate(roomDetails.getDate());
        room.setStarHour(roomDetails.getStarHour());
        room.setEndHour(roomDetails.getEndHour());
        final Room updateRoom = roomRepository.save(room);
        return ResponseEntity.ok(updateRoom);
    }
}